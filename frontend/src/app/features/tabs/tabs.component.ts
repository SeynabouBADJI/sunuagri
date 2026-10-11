import { Component } from '@angular/core';
import { IonicSharedModule } from '../../shared/ionic-shared.module';

@Component({
  selector: 'app-tabs',
  standalone: true,
  imports: [IonicSharedModule],
  templateUrl: './tabs.component.html',
  styleUrls: ['./tabs.component.scss'],
})
export class TabsComponent {}