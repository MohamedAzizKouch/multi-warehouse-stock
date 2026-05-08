import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const credentials = localStorage.getItem('credentials');

  if (credentials) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Basic ${credentials}`
      }
    });
    return next(cloned);
  }

  return next(req);
};