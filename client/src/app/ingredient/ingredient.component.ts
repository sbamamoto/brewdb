import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-ingredient',
  templateUrl: './ingredient.component.html',
  styleUrls: ['./ingredient.component.css']
})
export class IngredientComponent implements OnInit {
  ingredients:any;

    constructor(private http:HttpClient, private router:Router) { }

    ngOnInit() {
        this.http.get('http://localhost:8080/ingredient').subscribe( data=> {
            this.ingredients = data;
            //console.log("Hallo: [" +data[0].source.id+"]") 
        });
    }
    
    deleteIngredient(id){
        this.http.delete('http://localhost:8080/ingredient/'+id).subscribe( data=> {
            this.ingredients = data;
        });
    }
}
