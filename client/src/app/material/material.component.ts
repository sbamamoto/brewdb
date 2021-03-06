import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-material',
  templateUrl: './material.component.html',
  styleUrls: ['./material.component.css']
})
export class MaterialComponent implements OnInit {

    materials:any;

    constructor(private http:HttpClient, private router:Router) { }

    ngOnInit() {
        this.http.get('/api/material').subscribe( data=> {
            this.materials = data;
            console.log("Hallo: [" +data[0].notes+"]") 
        });
    }
    
    deleteMaterial(id){
        this.http.delete('/api/material/'+id).subscribe( data=> {
            this.materials = data;
        });
    }
}
