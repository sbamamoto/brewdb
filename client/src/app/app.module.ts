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
import { ReceiptComponent } from './receipt/receipt.component';
import { ReceiptEditComponent } from './receipt-edit/receipt-edit.component';
import { StepComponent } from './step/step.component';
import { StepEditComponent } from './step-edit/step-edit.component';
import { BrewerComponent } from './brewer/brewer.component';
import { BrewerEditComponent } from './brewer-edit/brewer-edit.component';
import { BrewerDetailComponent } from './brewer-detail/brewer-detail.component';
import { AccountComponent } from './account/account.component';
import { StorageComponent } from './storage/storage.component';
import { LoginComponent } from './login/login.component';
import { AuthService } from './auth.service';
import { AuthGuard } from './auth.guard';
import { UserService } from './user.service';


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
    MaterialEditComponent,
    ReceiptComponent,
    ReceiptEditComponent,
    StepComponent,
    StepEditComponent,
    BrewerComponent,
    BrewerEditComponent,
    BrewerDetailComponent,
    AccountComponent,
    StorageComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule.forRoot()
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}, AuthService, AuthGuard, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
