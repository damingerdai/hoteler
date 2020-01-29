import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LayoutComponent } from './layout.component';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [LayoutComponent],
  imports: [
    CommonModule,

    SharedModule
  ]
})
export class LayoutModule { }
