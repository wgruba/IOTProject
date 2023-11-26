import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainSiteComponent } from './main-site/main-site.component';
import { LoginSiteComponent } from './login-site/login-site.component';
import { RegisterSiteComponent } from './register-site/register-site.component';
import { DescriptionPageComponent } from './description-page/description-page.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { UserHelpComponent } from './user-help/user-help.component';
import { AddEventSiteComponent } from './add-event-site/add-event-site.component';
import { UserSearchingPageComponent } from './user-searching-page/user-searching-page.component';
import { AuthGuard } from './auth.guard';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserEventsComponent } from './user-events/user-events.component';
import { RoleGuard } from './role.guard';


const routes: Routes = [
  { path: 'home', component: MainSiteComponent},
  { path: 'login-site', component:LoginSiteComponent},
  { path: 'register-site', component:RegisterSiteComponent},
  { path: 'event-details/:id', component: DescriptionPageComponent, canActivate: [RoleGuard], data: { requiredRole: 'Admin' }},
  { path: 'o-nas', component:AboutUsComponent},
  { path: 'pomoc', component:UserHelpComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'add-event', component: AddEventSiteComponent, canActivate: [AuthGuard, RoleGuard],  data: { requiredRole:  ['VERIFIED_USER, UNVERIFIED_USER'] }},
  { path: 'event-searching', component: UserSearchingPageComponent, canActivate: [RoleGuard], data: { requiredRole: ['VERIFIED_USER, UNVERIFIED_USER'] } },
  { path: 'user-profile', component: UserProfileComponent, canActivate: [AuthGuard, RoleGuard],  data: { requiredRole:  ['VERIFIED_USER, UNVERIFIED_USER'] } },
  { path: 'user-events', component: UserEventsComponent, canActivate: [AuthGuard, RoleGuard],  data: { requiredRole:  ['VERIFIED_USER, UNVERIFIED_USER'] } },

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
