import {ActionsUnion, createAction} from "../../helpers/action.helper";
import {SET_CURRENT_USER} from "./actionTypes";
import {ICurrentUser} from "../../api/auth/authModels";

export const authActions = {
    setCurrentUser: (currentUser?: ICurrentUser) => createAction(SET_CURRENT_USER, currentUser),
    removeCurrentUser: () => createAction(SET_CURRENT_USER, undefined),
};

export type AuthActions = ActionsUnion<typeof authActions>;
