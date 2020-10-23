export interface IPmsKunden {
  id?: number;
  name1?: string;
  name2?: string;
}

export class PmsKunden implements IPmsKunden {
  constructor(public id?: number, public name1?: string, public name2?: string) {}
}
