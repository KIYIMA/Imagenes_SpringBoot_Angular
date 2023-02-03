import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SelectImageComponent } from './select-image/select-image.component';

const routes: Routes = [
    {path:'addImage', component: SelectImageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
