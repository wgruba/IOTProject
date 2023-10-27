import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainSiteComponent } from './main-site/main-site.component';

const routes: Routes = [
  { path: '', component: MainSiteComponent }, // Default route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
