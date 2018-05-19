import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-material-edit',
  templateUrl: './material-edit.component.html',
  styleUrls: ['./material-edit.component.css']
})
export class MaterialEditComponent implements OnInit {

  material = {};
  types = [];
  type = 0;
  
  constructor(private http:HttpClient, private router:Router, private route:ActivatedRoute) { }

  ngOnInit() {
      let id;
      if (this.route.snapshot.params['id'] != null) {
          id = this.route.snapshot.params['id'];
      }
      else {
          id = '-1';
      }
    
    this.http.get('http://localhost:8080/material/'+id).subscribe( data => {
        this.material = data['material'];
        this.types = data['types'];
        if (id != '-1') {
            this.type = this.material['types'].id;
            console.log("source: "+this.material['types'].id);
        }
    }, (err) => {
        console.log(err);
    }
   );  

  }
  
  saveMaterial(id) {
      if (id == null) {
          console.log("creating material.");
          this.http.post ('http://localhost:8080/ingredient', [this.material, this.type]).subscribe(res => {
                  console.log("material sent");
                  let id = res['id'];
                  this.router.navigate(['/materials/']);
             }, (err) => {
                  console.log(err);
             }
        );
      }
      else {
        this.http.put('http://localhost:8080/material/'+id, [this.material, this.type]).subscribe (res => { 
            let id = res['id'];
            this.router.navigate(['/materials/']);
         }, (err) => {
            console.log(err);
        });
      }
  }

}
