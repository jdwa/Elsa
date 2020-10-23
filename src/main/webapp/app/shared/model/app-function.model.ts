export interface IAppFunction {
  id?: number;
  name?: string;
  description?: string;
}

export class AppFunction implements IAppFunction {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
