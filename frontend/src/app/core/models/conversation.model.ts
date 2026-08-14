export interface Message {
  id: number;
  contenu: string;
  dateEnvoi: string;
  type: 'UTILISATEUR' | 'ASSISTANT';
  conversationId: number;
}

export interface ConversationIA {
  id: number;
  dateCreation: string;
  utilisateurId: number;
}