import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ThemeService } from '../../core/services/theme.service';
import { Option, Profile } from '../../core/domain/modules';
import { ProfileService } from '../../core/services/profile.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  profiles$: Observable<Profile[]> = new Observable<Profile[]>();

  options$: Observable<Array<Option>> = this.themeService.getThemeOptions();

  constructor(private readonly themeService: ThemeService, private profileService: ProfileService) {}

  ngOnInit() {
    const theme = this.themeService.getTheme();

    if (theme === undefined) {
      this.themeService.setTheme('deeppurple-amber');
    } else {
      this.themeService.setTheme(theme);
    }

    this.profiles$ = this.profileService.findAll();
  }

  themeChangeHandler(themeToSet) {
    this.themeService.setTheme(themeToSet);
  }

  goToGitHub() {
    window.open('https://github.com/peavers/files', '_blank');
  }

  addProfile() {
    this.profileService.addProfile();
  }
}
