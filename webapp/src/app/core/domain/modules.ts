export interface Option {
  backgroundColor: string;
  buttonColor: string;
  headingColor: string;
  label: string;
  value: string;
}

export interface Root {
  projects: Profile[];
}

export interface Profile {
  id?: string;
  name?: string;

  monitorDirectory?: string;
  copyToDirectory?: string;

  deleteThreshold?: number;
  deleteSmallFiles?: boolean;
  deleteEmptyDirectories?: boolean;
  copyMediaFiles?: boolean;

  logItems?: LogItem[];
}

export interface LogItem {
  message: string;
}
