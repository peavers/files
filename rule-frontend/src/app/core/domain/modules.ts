export interface Option {
  backgroundColor: string;
  buttonColor: string;
  headingColor: string;
  label: string;
  value: string;
}

export interface RuleDto {
  type?: string
  id?: string
  name?: String
  enabled?: boolean

  targetDirectory?: string;
  sourceDirectory?: string;
  lessThanThreshold?: number;
  greaterThanThreshold?: number;
}
