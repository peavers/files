import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared';
import { DefaultComponent } from './pages/default/default.component';
import { RoutingModule } from './home.routing';
import { ProfileComponent } from './pages/profile/profile.component';

@NgModule({
  declarations: [DefaultComponent, ProfileComponent],
  imports: [SharedModule, RoutingModule],
  exports: [],
  entryComponents: [],
})
export class HomeModule {}
