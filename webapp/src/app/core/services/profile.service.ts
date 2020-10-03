import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { Profile, Root } from '../domain/modules';
import { Injectable } from '@angular/core';
import {
  ProfileDialogComponent,
  ProfileDialogData,
} from '../../shared/components/profile-dialog/profile-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private readonly endpoint;

  private profiles$: BehaviorSubject<Profile[]> = new BehaviorSubject([]);

  constructor(private httpClient: HttpClient, private snackbar: MatSnackBar, private dialog: MatDialog) {
    this.endpoint = `${environment.api}/root/profile`;

    this.httpClient
      .get<Profile[]>(`${this.endpoint}`)
      .subscribe((profiles: Profile[]) => this.profiles$.next(profiles));
  }

  findAll(): Observable<Profile[]> {
    return this.profiles$.asObservable();
  }

  findById(id: string): Observable<Profile> {
    return this.httpClient.get<Profile>(`${this.endpoint}/${id}`);
  }

  addProfile() {
    const data: ProfileDialogData = { profile: {} };

    this.dialog
      .open(ProfileDialogComponent, { data: data, disableClose: true })
      .afterClosed()
      .subscribe((feed) => {
        if (feed === undefined) return;

        this.save(feed);
      });
  }

  editProfile(profile: Profile) {
    const data: ProfileDialogData = { profile: profile };

    this.dialog
      .open(ProfileDialogComponent, { data: data, disableClose: true })
      .afterClosed()
      .subscribe((feed) => {
        if (feed === undefined) return;

        this.save(feed);
      });
  }

  save(profile: Profile): void {
    this.httpClient.post<Profile>(`${this.endpoint}`, profile).subscribe((result: Profile) => {
      if (!this.profiles$.value.some((f: Profile) => f.id === result.id)) {
        this.profiles$.next([...this.profiles$.value, result]);
      }

      this.snackbar.open('Saved profile');
    });
  }
}
