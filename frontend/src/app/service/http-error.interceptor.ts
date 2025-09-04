import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandlerFn,
  HttpInterceptorFn,
  HttpRequest
} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

export const httpErrorInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> => {
  return next(req).pipe(
    catchError((error: unknown) => {
      if (error instanceof HttpErrorResponse) {
        const serverMessage = (error.error && (error.error.message || error.message)) || 'Request failed';
        const friendly = `${error.status || ''} ${serverMessage}`.trim();
        return throwError(() => new HttpErrorResponse({
          error: { message: friendly },
          status: error.status,
          statusText: error.statusText,
          url: error.url ?? undefined
        }));
      }
      return throwError(() => error);
    })
  );
};
