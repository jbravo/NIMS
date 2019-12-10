export class Cable {
  private _name: String;

  get name() {
    return this._name;
  }

  set name(name: String) {
    this._name = name;
  }

  private _id: number;

  get id() {
    return this._id;
  }

  set id(id: number) {
    this._id = id;
  }

  private _cableTypeId: number;

  get cableTypeId() {
    return this._cableTypeId;
  }

  set cableTypeId(cableTypeId: number) {
    this._cableTypeId = cableTypeId;
  }
}
