import { Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { ProfileService } from '../../../../core/services/profile.service';
import { Observable, Subscription } from 'rxjs';
import { Profile } from '../../../../core/domain/modules';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute, Router } from '@angular/router';

export interface Feature {
  name: string;
  description: string;
}

@Component({
  selector: 'app-default',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ProfileComponent implements OnInit, OnDestroy {
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  subscriptions: Subscription[] = [];

  profile: Profile;

  displayedColumns: string[] = ['message', 'object'];

  dataSource = new MatTableDataSource();

  constructor(private route: ActivatedRoute, private router: Router, private profileService: ProfileService) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.route.params.subscribe((response) => {
        this.profileService.findById(response.id).subscribe((profile) => {
          this.profile = profile;
          this.dataSource.data = profile.logItems;
        });
      })
    );
  }

  saveProfile() {
    this.profileService.save(this.profile);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
