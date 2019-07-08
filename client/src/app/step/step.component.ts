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
  receiptId: string;

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    if (this.route.snapshot.params['id'] != null) {
      this.receiptId = this.route.snapshot.params['id'];
    }
    else {
      this.receiptId = '-1';
    }
   
    this.http.get('/api/stepList/'+this.receiptId).subscribe(data => {
      console.log(' ##ngoninit## ' + data);      
      this.stepInfo = data;
      console.log('*** '+this.stepInfo.steps.length);
      console.log(this.stepInfo);
      console.log(this.stepInfo.steps);      
    });
  }

  up (stepId) {
    this.http.get('/api/stepList/'+this.receiptId+'?up='+stepId).subscribe(data => {
      console.log(' ##### ' + data);      
      this.stepInfo = data;
      console.log(this.stepInfo);
      console.log(this.stepInfo.steps);      
    });
  }

  down (stepId) {
    this.http.get('/api/stepList/'+this.receiptId+'?down='+stepId).subscribe(data => {
      console.log(' ##### ' + data);      
      this.stepInfo = data;
      console.log(this.stepInfo);
      console.log(this.stepInfo.steps);      
    });
  }

  deleteStep (stepId) {
    this.http.delete('/api/step/'+this.receiptId+"?stepId="+stepId).subscribe(data => {
      console.log(' ##### ' + data);      
      this.stepInfo = data;
      console.log(this.stepInfo);
      console.log(this.stepInfo.steps);      
    });
  }

}
