import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { SelectImageComponent } from './select-image/select-image.component';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './index/index.component';
import { HomeComponent } from './home/home.component';

const appRoutes:Routes=[
  {path:'', component:HomeComponent},
  {path:'index', component:IndexComponent},
  {path:'image/edit', component:SelectImageComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    SelectImageComponent,
    IndexComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
