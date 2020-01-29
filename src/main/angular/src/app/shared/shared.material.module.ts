import { NgModule } from '@angular/core';

import { MatToolbarModule } from '@angular/material/toolbar';

const materials = [
  MatToolbarModule
]


@NgModule({
  declarations: [],
  imports: [
    ...materials
  ],
  exports: [
    ...materials
  ]
})
export class SharedMaterialModule { }
