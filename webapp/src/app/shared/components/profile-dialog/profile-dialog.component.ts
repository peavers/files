import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Profile } from '../../../core/domain/modules';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';

export interface ProfileDialogData {
  profile: Profile;
}

@Component({
  selector: 'profile-dialog',
  templateUrl: './profile-dialog.component.html',
  styleUrls: ['./profile-dialog.component.scss'],
})
export class ProfileDialogComponent {
  profile: Profile;

  constructor(private dialogRef: MatDialogRef<any>, @Inject(MAT_DIALOG_DATA) private data: ProfileDialogData) {
    this.profile = data.profile;
  }

  onCancel() {
    this.dialogRef.close();
  }
}
