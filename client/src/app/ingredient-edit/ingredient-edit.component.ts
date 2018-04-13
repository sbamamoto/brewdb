import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-ingredient-edit',
  templateUrl: './ingredient-edit.component.html',
  styleUrls: ['./ingredient-edit.component.css']
})
export class IngredientEditComponent implements OnInit {

  ingredient = {};
  sources = [];
  sourceid = 0;
  
  constructor(private http:HttpClient, private router:Router, private route:ActivatedRoute) { }

  ngOnInit() {
      let id;
      if (this.route.snapshot.params['id'] != null) {
          id = this.route.snapshot.params['id'];
      }
      else {
          id = '-1';
      }
    
    this.http.get('http://localhost:8080/ingredient/'+id).subscribe( data => {
        this.ingredient = data['ingredient'];
        this.sources = data['sources'];
        if (id != '-1') {
            this.sourceid = this.ingredient['source'].id;
            console.log("source: "+this.ingredient['source'].id);
        }
    }, (err) => {
        console.log(err);
    }
   );  

  }
  
  saveIngredient(id) {
      if (id == null) {
          console.log("creating ingredient.");
          this.http.post ('http://localhost:8080/ingredient', [this.ingredient, this.sourceid]).subscribe(res => {
                  console.log("ingredient sent");
                  let id = res['id'];
                  this.router.navigate(['/ingredients/']);
             }, (err) => {
                  console.log(err);
             }
        );
      }
      else {
        this.http.put('http://localhost:8080/ingredient/'+id, [this.ingredient, this.sourceid]).subscribe (res => { 
            let id = res['id'];
            this.router.navigate(['/ingredients/']);
         }, (err) => {
            console.log(err);
        });
      }
  }
}
