export class WeldSleeve {
    _sourceCableId: number;
    _sourceLineNo: number[];
    _sleeveId: number;
    _destCableId: number;
    _destLineNo: number[];
    _attenuation: number;
    _createUser: String;
    _note: String;
    get sourceCableId() {
        return this._sourceCableId;
    }
    set sourceCableId(sourceCableId: number) {
        this._sourceCableId = sourceCableId;
    }

    get sourceLineNo() {
        return this._sourceLineNo;
    }
    set sourceLineNo(sourceLineNo: number[]) {
        this._sourceLineNo = sourceLineNo;
    }

    get sleeveId() {
        return this._sleeveId;
    }
    set sleeveId(sleeveId: number) {
        this._sleeveId = sleeveId;
    }

    get destCableId() {
        return this._destCableId;
    }
    set destCableId(destCableId: number) {
        this._destCableId = destCableId;
    }

    get destLineNo() {
        return this._destLineNo;
    }
    set destLineNo(destLineNo: number[]) {
        this._destLineNo = destLineNo;
    }

    get attenuation() {
        return this._attenuation;
    }
    set attenuation(attenuation: number) {
        this._attenuation = attenuation;
    }

    get createUser() {
        return this._createUser;
    }
    set createUser(_createUser: String) {
        this._createUser = _createUser;
    }

    get note() {
        return this._note;
    }
    set note(note: String) {
        this._note = note;
    }
}