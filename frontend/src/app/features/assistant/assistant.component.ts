import { Component, OnInit, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { AssistantService } from '../../core/services/assistant.service';
import { Message } from '../../core/models/conversation.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-assistant',
  standalone: true,
  imports: [CommonModule, FormsModule, IonicModule,RouterLink],
  templateUrl: './assistant.component.html',
  styleUrls: ['./assistant.component.scss'],
})
export class AssistantComponent implements OnInit, AfterViewChecked {
  @ViewChild('scrollArea') scrollArea?: ElementRef<HTMLElement>;

  messages: Message[] = [];
  saisie = '';
  enTrainDecrire = false;

  suggestions = [
    'Comment traiter le mildiou ?',
    'Quand semer le mil ?',
    'Comment ameliorer ma recolte ?',
  ];

  constructor(private assistantService: AssistantService) {}

  ngOnInit() {
    this.assistantService.getMessages().subscribe(m => (this.messages = m));
  }

  ngAfterViewChecked() {
    this.scrollBas();
  }

  envoyer(texte?: string) {
    const contenu = (texte ?? this.saisie).trim();
    if (!contenu) return;
    this.saisie = '';
    this.enTrainDecrire = true;

    this.assistantService.envoyerMessage(contenu).subscribe(() => {
      this.assistantService.getMessages().subscribe(m => {
        this.messages = m;
        this.enTrainDecrire = false;
      });
    });
  }

  private scrollBas() {
    const el = this.scrollArea?.nativeElement;
    if (el) el.scrollTop = el.scrollHeight;
  }
}