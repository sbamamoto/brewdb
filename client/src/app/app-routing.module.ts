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


const routes: Routes = [
  {path: '', redirectTo: '/receipts', pathMatch: 'full'},
  {path: 'receipts', component: ReceiptComponent, data: {title: 'Receipt List'}},
  {path: 'sources', component: SourceComponent, data: {title: 'Sources List'}},
  {path: 'source-create', component: SourceCreateComponent, data: {title: 'Add Source'}},  
  {path: 'source-details/:id', component: SourceDetailComponent, data: {title: 'Source Details'}},  
  {path: 'source-edit/:id', component: SourceEditComponent, data: {title: 'Source Edit'}},
  {path: 'ingredients', component: IngredientComponent, data: {title: 'Ingredients List'}},
  {path: 'ingredient/:id', component: IngredientComponent, data: {title: 'Ingredients Delete'}},
  {path: 'ingredient-edit/:id', component: IngredientEditComponent, data: {title: 'Ingredients Edit'}},
  {path: 'ingredient-edit', component: IngredientEditComponent, data: {title: 'Ingredients Add'}},
  {path: 'materials', component: MaterialComponent, data: {title: 'Materials List'}},
  {path: 'material/:id', component: MaterialComponent, data: {title: 'Materials Delete'}},
  {path: 'material-edit/:id', component: MaterialEditComponent, data: {title: 'Materials Edit'}},
  {path: 'material-edit', component: MaterialEditComponent, data: {title: 'Materials Add'}},
  {path: 'receipt/:id', component: ReceiptComponent, data: {title: 'Receipt Delete'}},
  {path: 'receipt-edit/:id', component: ReceiptEditComponent, data: {title: 'Receipt Edit'}},
  {path: 'receipt-edit', component: ReceiptEditComponent, data: {title: 'Receipt Add'}}
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}