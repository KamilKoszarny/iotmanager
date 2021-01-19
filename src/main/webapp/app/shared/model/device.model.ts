export interface IDevice {
  id?: number;
  name?: string;
  serialNo?: string;
  modelId?: number;
  siteId?: number;
}

export class Device implements IDevice {
  constructor(public id?: number, public name?: string, public serialNo?: string, public modelId?: number, public siteId?: number) {}
}
