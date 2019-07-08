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

    constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

    ngOnInit() {
        let id;
        if (this.route.snapshot.params['id'] != null) {
            id = this.route.snapshot.params['id'];
        }
        else {
            id = '-1';
        }

        this.http.get('/api/material/' + id).subscribe(data => {
            this.material = data['material'];
            this.types = data['types'];
        }, (err) => {
            console.log(err);
        }
        );
    }

    saveMaterial(id) {
        if (id == null) {
            console.log("creating material." + this.type);
            this.http.post('/api/material', [this.material]).subscribe(res => {
                console.log("material sent");
                let id = res['id'];
                this.router.navigate(['/materials/']);
            }, (err) => {
                console.log(err);
            }
            );
        }
        else {
            this.http.put('/api/material/' + id, [this.material]).subscribe(res => {
                let id = res['id'];
                this.router.navigate(['/materials/']);
            }, (err) => {
                console.log(err);
            });
        }
    }

}
