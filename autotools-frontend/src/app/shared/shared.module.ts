import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { MaterialModule } from './material.module';

import { FileSizePipe } from './pipes/file-size.pipe';
import { ThemeSwitcherComponent } from './components/theme-switcher/theme-switcher.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ActionButtonComponent } from './components/action-button/action-button.component';
import { RuleDialogComponent } from './components/rule-dialog/rule-dialog.component';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';

@NgModule({
  imports: [CommonModule, FormsModule, MatProgressSpinnerModule, MaterialModule, ReactiveFormsModule, RouterModule],
  declarations: [
    FileSizePipe,
    ThemeSwitcherComponent,
    ActionButtonComponent,
    RuleDialogComponent,
    ConfirmDialogComponent,
  ],
  exports: [
    CommonModule,
    ActionButtonComponent,
    FileSizePipe,
    FormsModule,
    HttpClientModule,
    MaterialModule,
    ReactiveFormsModule,
    RouterModule,
    ThemeSwitcherComponent,
  ],
  entryComponents: [],
  providers: [RuleDialogComponent, ConfirmDialogComponent],
})
export class SharedModule {}
