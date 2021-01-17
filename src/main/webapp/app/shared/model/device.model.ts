export interface IDevice {
  id?: number;
  serialNo?: string;
  deviceModelId?: number;
  siteId?: number;
}

export class Device implements IDevice {
  constructor(public id?: number, public serialNo?: string, public deviceModelId?: number, public siteId?: number) {}
}
