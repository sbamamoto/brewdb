import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { IndexComponent } from './index/index.component';
import { AppComponent } from './app.component';
//import { NavComponent } from './nav/nav.component';
//import { NavService } from './nav/nav.service';
import { AppRoutingModule } from "./app-routing.module";
import { HttpClientModule } from "@angular/common/http";
import { SourceComponent } from './source/source.component';
import { SourceCreateComponent } from './source-create/source-create.component';
import { SourceDetailComponent } from './source-detail/source-detail.component';
import { SourceEditComponent } from './source-edit/source-edit.component';
import { IngredientComponent } from './ingredient/ingredient.component';
import { IngredientEditComponent } from './ingredient-edit/ingredient-edit.component';
import { MaterialComponent } from './material/material.component';
import { MaterialEditComponent } from './material-edit/material-edit.component';


@NgModule({
  declarations: [
    AppComponent,
//    NavComponent,
    IndexComponent,
    SourceComponent,
    SourceCreateComponent,
    SourceDetailComponent,
    SourceEditComponent,
    IngredientComponent,
    IngredientEditComponent,
    MaterialComponent,
    MaterialEditComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule.forRoot()
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
