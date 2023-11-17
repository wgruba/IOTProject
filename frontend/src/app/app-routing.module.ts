import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainSiteComponent } from './main-site/main-site.component';
import { LoginSiteComponent } from './login-site/login-site.component';
import { RegisterSiteComponent } from './register-site/register-site.component';
import { DescriptionPageComponent } from './description-page/description-page.component';
import { AddEventSiteComponent } from './add-event-site/add-event-site.component';
import { UserSearchingPageComponent } from './user-searching-page/user-searching-page.component';
import { AuthGuard } from './auth.guard';


const routes: Routes = [
  { path: 'home', component: MainSiteComponent },
  { path: 'login-site', component:LoginSiteComponent},
  { path: 'register-site', component:RegisterSiteComponent},
  { path: 'event-details/:id', component: DescriptionPageComponent },
  { path: 'add-event', component: AddEventSiteComponent, canActivate: [AuthGuard] }, // Securing this route
  { path: 'event-searching', component: UserSearchingPageComponent },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
