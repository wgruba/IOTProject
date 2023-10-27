import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainSiteComponent } from './main-site/main-site.component';
import { LoginSiteComponent } from './login-site/login-site.component';
import { RegisterSiteComponent } from './register-site/register-site.component';

const routes: Routes = [
  { path: '', component: MainSiteComponent }, // Default route
  { path: 'login-site', component:LoginSiteComponent},
  { path: 'register-site', component:RegisterSiteComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
