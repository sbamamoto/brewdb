import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-storage-edit',
  templateUrl: './storage-edit.component.html',
  styleUrls: ['./storage-edit.component.css']
})



export class StorageEditComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  types: any;
  materials: any;
  allMaterials: any;
  storage: any;
  response: any;
  matNames: any;

  ngOnInit() {
    let id;
    if (this.route.snapshot.params['id'] != null) {
      id = this.route.snapshot.params['id'];
    }
    else {
      id = '-1';
    }

    this.http.get('/api/storage/' + id).subscribe(data => {
      console.log(data);
      this.response = data;
      this.types = this.response.types;
      this.allMaterials = this.response.materials;
      this.materials = [];
      this.storage = this.response.storage;
      this.storage.unit = 'GRAMM';
      console.log(this.storage);
    }, (err) => {
      console.log(err);
    }
    );
  }

  onChange(deviceValue) {
    this.materials=[];
    console.log(this.allMaterials);
    this.allMaterials.forEach(element => {
      if (element.type === deviceValue) {
        this.materials.push(element);
      }
    });
  }

}
