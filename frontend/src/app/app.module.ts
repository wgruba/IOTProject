import { CUSTOM_ELEMENTS_SCHEMA,NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { register } from 'swiper/element/bundle';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainSiteComponent } from './main-site/main-site.component';
import { UserHeaderComponent } from './user-header/user-header.component';
import { UserFooterComponent } from './user-footer/user-footer.component';
import { SwiperDirective } from './swiper.directive';
import { RegisterSiteComponent } from './register-site/register-site.component';
import { LoginSiteComponent } from './login-site/login-site.component';
import { DescriptionPageComponent } from './description-page/description-page.component';

register();

@NgModule({
  declarations: [
    AppComponent,
    MainSiteComponent,
    LoginSiteComponent,
    RegisterSiteComponent,
    UserHeaderComponent,
    UserFooterComponent,
    SwiperDirective,
    DescriptionPageComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class AppModule { }
