import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-brewer-edit',
  templateUrl: './brewer-edit.component.html',
  styleUrls: ['./brewer-edit.component.css']
})

export class BrewerEditComponent implements OnInit {

  brewer: any;

  constructor(private http: HttpClient, private router: Router, private route:ActivatedRoute) { }

  ngOnInit() {
    let id;
    if (this.route.snapshot.params['id'] != null) {
        id = this.route.snapshot.params['id'];
    }
    else {
        id = '-1';
    }
    this.http.get('/api/brewer/' + id).subscribe(data => {
        console.log(data);
        this.brewer = data;
      }, (err) => {
        console.log(err);
      }
    );
  }

  saveBrewer(id) {
    if (id == null) {
      this.http.post('/api/brewer', [this.brewer]).subscribe(res => {
        console.log("brewer sent");
        console.log(this.brewer);
        let id = res['id'];
        this.router.navigate(['/brewers/']);
      }, (err) => {
        console.log(err);
      }
      );
    }
    else {
      this.http.put('/api/brewer/' + id, [this.brewer]).subscribe(res => {
        let id = res['id'];
        this.router.navigate(['/brewers/']);
      }, (err) => {
        console.log(err);
      });
    }
  }

}
