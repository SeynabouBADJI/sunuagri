import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
import { MockDataService } from './mock-data.service';
import { Message } from '../models/conversation.model';

@Injectable({ providedIn: 'root' })
export class AssistantService {
  constructor(private mock: MockDataService) {}

  getMessages(): Observable<Message[]> {
    return of(this.mock.messages).pipe(delay(200));
  }

  envoyerMessage(contenu: string): Observable<Message> {
    const messageUtilisateur: Message = {
      id: this.mock.messages.length + 1,
      contenu,
      dateEnvoi: new Date().toISOString(),
      type: 'UTILISATEUR',
      conversationId: this.mock.conversation.id,
    };
    this.mock.messages.push(messageUtilisateur);

    const reponse: Message = {
      id: this.mock.messages.length + 1,
      contenu: 'Ceci est une reponse simulee de l\'assistant. Ta question etait : "' + contenu + '"',
      dateEnvoi: new Date().toISOString(),
      type: 'ASSISTANT',
      conversationId: this.mock.conversation.id,
    };
    this.mock.messages.push(reponse);

    return of(reponse).pipe(delay(900));
  }
}