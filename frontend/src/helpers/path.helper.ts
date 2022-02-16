import { join } from "path";

export const getOsEnv = (key: string): string => process.env[key] as string;

export const getPath = (path: string): string => join(process.cwd(), path);

export const getPaths = (paths: string[]): string[] =>
  paths.map(p => getPath(p));
