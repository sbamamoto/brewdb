import {NgModule} from '@angular/core';
import {RouterModule,Routes} from '@angular/router';
import {IndexComponent} from "./index/index.component";
import {SourceComponent} from "./source/source.component";
import {SourceCreateComponent} from "./source-create/source-create.component";
import {SourceDetailComponent} from "./source-detail/source-detail.component";
import {SourceEditComponent} from "./source-edit/source-edit.component";
import {IngredientComponent} from "./ingredient/ingredient.component";
import {IngredientEditComponent} from "./ingredient-edit/ingredient-edit.component";
import {MaterialComponent} from "./material/material.component";
import {MaterialEditComponent} from "./material-edit/material-edit.component";
import {ReceiptComponent} from "./receipt/receipt.component";
import {ReceiptEditComponent} from "./receipt-edit/receipt-edit.component";
import {StepComponent} from "./step/step.component";
import {StepEditComponent} from "./step-edit/step-edit.component";
import {BrewerComponent} from "./brewer/brewer.component";
import {BrewerEditComponent} from "./brewer-edit/brewer-edit.component";
import {BrewerDetailComponent} from "./brewer-detail/brewer-detail.component";
import {AccountComponent} from "./account/account.component";
import {StorageComponent} from "./storage/storage.component";
import {LoginComponent} from "./login/login.component";
import { AuthGuard } from './auth.guard';


const routes: Routes = [
  {path: '', redirectTo: '/receipts', pathMatch: 'full'},
  {path: 'receipts', component: ReceiptComponent, data: {title: 'Receipt List'}},
  {path: 'sources', component: SourceComponent, data: {title: 'Sources List'}, canActivate: [AuthGuard]},
  {path: 'source-create', component: SourceCreateComponent, data: {title: 'Add Source'}, canActivate: [AuthGuard]},  
  {path: 'source-details/:id', component: SourceDetailComponent, data: {title: 'Source Details'}, canActivate: [AuthGuard]},  
  {path: 'source-edit/:id', component: SourceEditComponent, data: {title: 'Source Edit'}, canActivate: [AuthGuard]},
  {path: 'ingredients', component: IngredientComponent, data: {title: 'Ingredients List'}, canActivate: [AuthGuard]},
  {path: 'ingredient/:id', component: IngredientComponent, data: {title: 'Ingredients Delete'}, canActivate: [AuthGuard]},
  {path: 'ingredient-edit/:id', component: IngredientEditComponent, data: {title: 'Ingredients Edit'}, canActivate: [AuthGuard]},
  {path: 'ingredient-edit', component: IngredientEditComponent, data: {title: 'Ingredients Add'}, canActivate: [AuthGuard]},
  {path: 'materials', component: MaterialComponent, data: {title: 'Materials List'}, canActivate: [AuthGuard]},
  {path: 'material/:id', component: MaterialComponent, data: {title: 'Materials Delete'}, canActivate: [AuthGuard]},
  {path: 'material-edit/:id', component: MaterialEditComponent, data: {title: 'Materials Edit'}, canActivate: [AuthGuard]},
  {path: 'material-edit', component: MaterialEditComponent, data: {title: 'Materials Add'}, canActivate: [AuthGuard]},
  {path: 'receipt/:id', component: ReceiptComponent, data: {title: 'Receipt Delete'}, canActivate: [AuthGuard]},
  {path: 'receipt-edit/:id', component: ReceiptEditComponent, data: {title: 'Receipt Edit'}, canActivate: [AuthGuard]},
  {path: 'receipt-edit', component: ReceiptEditComponent, data: {title: 'Receipt Add'}, canActivate: [AuthGuard]},
  {path: 'steps/:id', component: StepComponent, data: {title: 'Work on Steps'}, canActivate: [AuthGuard]},
  {path: 'step-up/:id/:up', component: StepComponent, data: {title: 'One Step Up'}, canActivate: [AuthGuard]},
  {path: 'step/:id/:stepId', component: StepComponent, data: {title: 'Step Delete'}, canActivate: [AuthGuard]},
  {path: 'step-edit/:id/:receiptId', component: StepEditComponent, data: {title: 'Step Edit'}, canActivate: [AuthGuard]},
  {path: 'step-edit', component: StepEditComponent, data: {title: 'Step Add'}, canActivate: [AuthGuard]},
  {path: 'brewers', component: BrewerComponent, data: {title: 'Brewers List'}, canActivate: [AuthGuard]},
  {path: 'brewer/:id', component: BrewerComponent, data: {title: 'Brewer Delete'}, canActivate: [AuthGuard]},
  {path: 'brewer-edit/:id', component: BrewerEditComponent, data: {title: 'Brewer Edit'}, canActivate: [AuthGuard]},
  {path: 'brewer-edit', component: BrewerEditComponent, data: {title: 'Brewer Add'}, canActivate: [AuthGuard]},
  {path: 'brewer-details/:id', component: BrewerDetailComponent, data: {title: 'Brewer Delete'}, canActivate: [AuthGuard]}, 
  {path: 'account', component: AccountComponent, data: {title: 'Account'}, canActivate: [AuthGuard]},
  {path: 'storage', component: StorageComponent, data: {title: 'Storage'}, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent, data: {title: 'Login'}},
  
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}