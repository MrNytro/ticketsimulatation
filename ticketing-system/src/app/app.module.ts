import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import { LiveUpdateComponent } from './components/live-update/live-update.component';
import { LogViewerComponent } from './components/log-viewer/log-viewer.component';

@NgModule({
  declarations: [
    AppComponent,
    ConfigurationComponent,
    LiveUpdateComponent,
    LogViewerComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }