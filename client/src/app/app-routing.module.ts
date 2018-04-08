import {NgModule} from '@angular/core';
import {RouterModule,Routes} from '@angular/router';
import {IndexComponent} from "./index/index.component";
import {SourceComponent} from "./source/source.component";
import {SourceCreateComponent} from "./source-create/source-create.component";
import {SourceDetailComponent} from "./source-detail/source-detail.component";
import {SourceEditComponent} from "./source-edit/source-edit.component";
import {IngredientComponent} from "./ingredient/ingredient.component";
import {IngredientEditComponent} from "./ingredient-edit/ingredient-edit.component";


const routes: Routes = [
  {path: '', redirectTo: '/sources', pathMatch: 'full'},
  {path: 'sources', component: SourceComponent, data: {title: 'Sources List'}},
  {path: 'source-create', component: SourceCreateComponent, data: {title: 'Add Source'}},  
  {path: 'source-details/:id', component: SourceDetailComponent, data: {title: 'Source Details'}},  
  {path: 'source-edit/:id', component: SourceEditComponent, data: {title: 'Source Edit'}},
  {path: 'ingredients', component: IngredientComponent, data: {title: 'Ingredients List'}},
  {path: 'ingredient/:id', component: IngredientComponent, data: {title: 'Ingredients Delete'}},
  {path: 'ingredient-edit/:id', component: IngredientEditComponent, data: {title: 'Ingredients Edit'}},
  {path: 'ingredient-edit', component: IngredientEditComponent, data: {title: 'Ingredients Add'}}
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}