import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {RuleDto} from "../../../core/domain/modules";

export interface RuleDialogData {
  rule: RuleDto;
}

@Component({
  selector: 'profile-dialog',
  templateUrl: './rule-dialog.component.html',
  styleUrls: ['./rule-dialog.component.scss'],
})
export class RuleDialogComponent {

  rules = [
    {
      type: 'RuleDeleteFiles',
      name: 'Delete files',
    },
    {
      type: 'RuleDeleteEmptyDirectories',
      name: 'Delete directories',
    },
    {
      type: 'RuleCopyMediaFiles',
      name: 'Copy files',
    },
  ];

  ruleDto: RuleDto

  constructor(private dialogRef: MatDialogRef<any>, @Inject(MAT_DIALOG_DATA) private data: RuleDialogData) {
    this.ruleDto = data.rule;
  }

  onCancel() {
    this.dialogRef.close();
  }
}
