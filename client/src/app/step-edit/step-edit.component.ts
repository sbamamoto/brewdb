import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { IngredientComponent } from '../ingredient/ingredient.component';

@Component({
  selector: 'app-step-edit',
  templateUrl: './step-edit.component.html',
  styleUrls: ['./step-edit.component.css']
})
export class StepEditComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }
  step: any;
  materialList: any;
  materials: any[] = [];
  receiptId: string;
  stepId: string;

  ngOnInit() {

    if (this.route.snapshot.params['id'] != null) {
      this.stepId = this.route.snapshot.params['id'];
    }
    else {
      this.stepId = '-1';
    }

    if (this.route.snapshot.params['receiptId'] != null) {
      this.receiptId = this.route.snapshot.params['receiptId'];
    }
    else {
      this.receiptId = '-1';
    }
 
    this.http.get('/api/step/' + this.stepId).subscribe(data => {
      console.log("##################  "+this.stepId);
      //console.log(" on init Data: "+data['ingredients'][0].units);
      this.step = data;
      console.log(this.step ['stepType']);
    });

    this.http.get('/api/material').subscribe(data => {
      console.log(data);
      this.materialList = data;
      console.log(this.materialList);
    });

    
  }

  addMaterial(ingredient) {
    console.log("######## "+ingredient);
    let m = {material:{id:ingredient.id}, units:ingredient.units, measure:ingredient.measure};
    this.step.ingredients.push(m);
    console.log(" +++++++ "+this.step.ingredients[0]);
  }

  deleteMaterial (id) {
    let index = this.step.ingredients.map(function(x) {return x.id; }).indexOf(id);
    this.step.ingredients.splice(index, 1);
  }

  saveStep(id) {
    this.step.receiptId=this.receiptId;
    
    this.materials.forEach(element => {
      console.log(" ---- "+element.name);
    });

    console.log(" ### "+this.stepId);
    // Storing the step 
    if (id == "-1") {
      this.http.post('http://localhost:8080/step', [this.step]).subscribe(res => {
        console.log("step sent");
        console.log(this.step);
    //    savedStepid = res['id'];
      }, (err) => {
        console.log(err);
      }
      );
    }
    else {
      this.http.put('http://localhost:8080/step/' + id, [this.step]).subscribe(res => {
      //  savedStepid = res['id'];
      }, (err) => {
        console.log(err);
      });
    }

    console.log ( "#### proceeding to receipt id:" + this.receiptId);
    this.router.navigate(['/steps/'+this.receiptId]);
  }
}