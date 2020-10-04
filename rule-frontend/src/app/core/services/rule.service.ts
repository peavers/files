import { HttpClient} from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { RuleDialogComponent, RuleDialogData } from '../../shared/components/rule-dialog/rule-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import {RuleDto} from "../domain/modules";

@Injectable({
  providedIn: 'root',
})
export class RuleService {
  private readonly endpoint;

  private profiles$: BehaviorSubject<RuleDto[]> = new BehaviorSubject([]);

  constructor(private httpClient: HttpClient, private snackbar: MatSnackBar, private dialog: MatDialog) {
    this.endpoint = `${environment.api}/root/rules`;

    this.httpClient
      .get<RuleDto[]>(`${this.endpoint}`)
      .subscribe((profiles: RuleDto[]) => this.profiles$.next(profiles));
  }

  findAll(): Observable<RuleDto[]> {
    return this.profiles$.asObservable();
  }

  addRule() {
    const data: RuleDialogData = { rule: {} };

    this.dialog
      .open(RuleDialogComponent, { data: data, disableClose: true })
      .afterClosed()
      .subscribe((rule: RuleDto) => {
        if (rule === undefined) return;

        this.save(rule);
      });
  }

  editRule(rule: RuleDto) {
    const data: RuleDialogData = { rule: rule };

    this.dialog
      .open(RuleDialogComponent, { data: data, disableClose: true })
      .afterClosed()
      .subscribe((rule) => {
        if (rule === undefined) return;

        this.save(rule);
      });
  }

  save(rule: RuleDto): void {
    this.httpClient.post<RuleDto>(`${this.endpoint}`, rule).subscribe((result: RuleDto) => {
      if (!this.profiles$.value.some((f: RuleDto) => f.id === result.id)) {
        this.profiles$.next([...this.profiles$.value, result]);
      }

      this.snackbar.open('Saved rule');
    });
  }
}
