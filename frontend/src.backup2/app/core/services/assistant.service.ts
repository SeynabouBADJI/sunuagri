import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap, map } from 'rxjs';

import { Message } from '../models/conversation.model';

interface AssistantResponse {
  contenu: string;
}

@Injectable({
  providedIn: 'root'
})
export class AssistantService {

  private readonly API_URL =
    'http://localhost:8080/api/assistant';

  private messagesSubject =
    new BehaviorSubject<Message[]>([
      {
        type: 'ASSISTANT',
        contenu:
          'Bonjour 👋 Je suis l’assistant SunuAgri. Comment puis-je t’aider ?'
      }
    ]);

  constructor(private http: HttpClient) {}

  getMessages(): Observable<Message[]> {
    return this.messagesSubject.asObservable();
  }

  envoyerMessage(contenu: string): Observable<Message> {

    const messageUtilisateur: Message = {
      type: 'UTILISATEUR',
      contenu
    };

    this.messagesSubject.next([
      ...this.messagesSubject.value,
      messageUtilisateur
    ]);

    return this.http
      .post<AssistantResponse>(
        `${this.API_URL}/message`,
        {
          message: contenu
        }
      )
      .pipe(

        map(response => {

          const messageAssistant: Message = {
            type: 'ASSISTANT',
            contenu: response.contenu
          };

          return messageAssistant;
        }),

        tap(messageAssistant => {

          this.messagesSubject.next([
            ...this.messagesSubject.value,
            messageAssistant
          ]);

        })
      );
  }
}