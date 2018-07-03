import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-step',
  templateUrl: './step.component.html',
  styleUrls: ['./step.component.css']
})
export class StepComponent implements OnInit {
  stepInfo: any;
  receiptId:any;

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    let id;
    if (this.route.snapshot.params['id'] != null) {
      id = this.route.snapshot.params['id'];
    }
    else {
      id = '-1';
    }
    this.receiptId = this.route.snapshot.params['receipt'];
    this.http.get('http://localhost:8080/stepList/'+id).subscribe(data => {
      console.log(data);      
      this.stepInfo = data;
    });
  }

}
