import {ApplicationConfig, importProvidersFrom} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgxGraphModule} from "@swimlane/ngx-graph";
import {BrowserModule} from "@angular/platform-browser";

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), importProvidersFrom(
    HttpClientModule, BrowserAnimationsModule,
    BrowserModule,
    NgxGraphModule
  )]
};
