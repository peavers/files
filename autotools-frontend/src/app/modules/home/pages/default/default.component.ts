import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { RuleService } from '../../../../core/services/rule.service';
import { Subscription } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { RuleDto } from '../../../../core/domain/modules';

@Component({
  selector: 'app-default',
  templateUrl: './default.component.html',
  styleUrls: ['./default.component.scss'],
})
export class DefaultComponent implements OnInit, OnDestroy {
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  displayedColumns: string[] = ['name', 'type', 'source', 'target', 'enabled', 'actions'];

  dataSource = new MatTableDataSource();

  subscriptions: Subscription[] = [];

  constructor(private router: Router, private profileService: RuleService) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.profileService.findAll().subscribe((root: RuleDto[]) => {
        this.dataSource.data = root;
        this.dataSource.sort = this.sort;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  addProfile() {
    this.profileService.addRule();
  }

  editRule(rule: RuleDto) {
    this.profileService.editRule(rule);
  }

  deleteRule(rule: RuleDto) {
    this.profileService.deleteRule(rule);
  }
}
