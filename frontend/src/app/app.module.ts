import { CUSTOM_ELEMENTS_SCHEMA,NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { register } from 'swiper/element/bundle';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainSiteComponent } from './main-site/main-site.component';
import { UserHeaderComponent } from './user-header/user-header.component';
import { UserFooterComponent } from './user-footer/user-footer.component';
import { SwiperDirective } from './swiper.directive';
import { RegisterSiteComponent } from './register-site/register-site.component';
import { LoginSiteComponent } from './login-site/login-site.component';
import { UserSidebarSearchComponent } from './user-sidebar-search/user-sidebar-search.component';
import { DescriptionPageComponent } from './description-page/description-page.component';
import { EventService } from './event.service';
import { MapComponent } from './map/map.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { AddEventSiteComponent } from './add-event-site/add-event-site.component';
import { UserProfileAsideComponent } from './user-profile-aside/user-profile-aside.component';
import { UserSearchingPageComponent } from './user-searching-page/user-searching-page.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserEventsComponent } from './user-events/user-events.component';


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
    UserSidebarSearchComponent
    DescriptionPageComponent,
    MapComponent,
    AddEventSiteComponent,
    UserProfileAsideComponent,
    UserSearchingPageComponent,
    UserProfileComponent,
    UserEventsComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    GoogleMapsModule,
    ReactiveFormsModule,
  ],
  providers: [EventService],
  bootstrap: [AppComponent],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class AppModule { }
