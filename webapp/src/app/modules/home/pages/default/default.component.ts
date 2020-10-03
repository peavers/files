import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ProfileService } from '../../../../core/services/profile.service';
import { Subscription } from 'rxjs';
import { Profile } from '../../../../core/domain/modules';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';

@Component({
  selector: 'app-default',
  templateUrl: './default.component.html',
  styleUrls: ['./default.component.scss'],
})
export class DefaultComponent implements OnInit, OnDestroy {
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  displayedColumns: string[] = ['name'];

  dataSource = new MatTableDataSource();

  subscriptions: Subscription[] = [];

  constructor(private router: Router, private profileService: ProfileService) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.profileService.findAll().subscribe((root: Profile[]) => {
        this.dataSource.data = root;
        this.dataSource.sort = this.sort;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  addProfile() {
    this.profileService.addProfile();
  }

  editProfile(profile: Profile) {
    this.profileService.editProfile(profile);
  }

  goTo(profile: Profile) {
    this.router.navigate(['/profile/' + profile.id]).then();
  }
}
