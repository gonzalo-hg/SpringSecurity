(()=>{"use strict";var e,v={},g={};function r(e){var f=g[e];if(void 0!==f)return f.exports;var t=g[e]={id:e,loaded:!1,exports:{}};return v[e].call(t.exports,t,t.exports,r),t.loaded=!0,t.exports}r.m=v,e=[],r.O=(f,t,n,i)=>{if(!t){var a=1/0;for(o=0;o<e.length;o++){for(var[t,n,i]=e[o],c=!0,s=0;s<t.length;s++)(!1&i||a>=i)&&Object.keys(r.O).every(b=>r.O[b](t[s]))?t.splice(s--,1):(c=!1,i<a&&(a=i));if(c){e.splice(o--,1);var u=n();void 0!==u&&(f=u)}}return f}i=i||0;for(var o=e.length;o>0&&e[o-1][2]>i;o--)e[o]=e[o-1];e[o]=[t,n,i]},(()=>{var f,e=Object.getPrototypeOf?t=>Object.getPrototypeOf(t):t=>t.__proto__;r.t=function(t,n){if(1&n&&(t=this(t)),8&n||"object"==typeof t&&t&&(4&n&&t.__esModule||16&n&&"function"==typeof t.then))return t;var i=Object.create(null);r.r(i);var o={};f=f||[null,e({}),e([]),e(e)];for(var a=2&n&&t;"object"==typeof a&&!~f.indexOf(a);a=e(a))Object.getOwnPropertyNames(a).forEach(c=>o[c]=()=>t[c]);return o.default=()=>t,r.d(i,o),i}})(),r.d=(e,f)=>{for(var t in f)r.o(f,t)&&!r.o(e,t)&&Object.defineProperty(e,t,{enumerable:!0,get:f[t]})},r.f={},r.e=e=>Promise.all(Object.keys(r.f).reduce((f,t)=>(r.f[t](e,f),f),[])),r.u=e=>e+"."+{233:"49926cc884ec010c74f7",487:"4863d5e7fcf4c416e424"}[e]+".js",r.miniCssF=e=>"styles.7f63387c0a1b6e1c0813.css",r.o=(e,f)=>Object.prototype.hasOwnProperty.call(e,f),(()=>{var e={},f="alumnos-app:";r.l=(t,n,i,o)=>{if(e[t])e[t].push(n);else{var a,c;if(void 0!==i)for(var s=document.getElementsByTagName("script"),u=0;u<s.length;u++){var l=s[u];if(l.getAttribute("src")==t||l.getAttribute("data-webpack")==f+i){a=l;break}}a||(c=!0,(a=document.createElement("script")).charset="utf-8",a.timeout=120,r.nc&&a.setAttribute("nonce",r.nc),a.setAttribute("data-webpack",f+i),a.src=r.tu(t)),e[t]=[n];var d=(_,b)=>{a.onerror=a.onload=null,clearTimeout(p);var h=e[t];if(delete e[t],a.parentNode&&a.parentNode.removeChild(a),h&&h.forEach(m=>m(b)),_)return _(b)},p=setTimeout(d.bind(null,void 0,{type:"timeout",target:a}),12e4);a.onerror=d.bind(null,a.onerror),a.onload=d.bind(null,a.onload),c&&document.head.appendChild(a)}}})(),r.r=e=>{"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},r.nmd=e=>(e.paths=[],e.children||(e.children=[]),e),(()=>{var e;r.tu=f=>(void 0===e&&(e={createScriptURL:t=>t},"undefined"!=typeof trustedTypes&&trustedTypes.createPolicy&&(e=trustedTypes.createPolicy("angular#bundler",e))),e.createScriptURL(f))})(),r.p="",(()=>{var e={666:0};r.f.j=(n,i)=>{var o=r.o(e,n)?e[n]:void 0;if(0!==o)if(o)i.push(o[2]);else if(666!=n){var a=new Promise((l,d)=>o=e[n]=[l,d]);i.push(o[2]=a);var c=r.p+r.u(n),s=new Error;r.l(c,l=>{if(r.o(e,n)&&(0!==(o=e[n])&&(e[n]=void 0),o)){var d=l&&("load"===l.type?"missing":l.type),p=l&&l.target&&l.target.src;s.message="Loading chunk "+n+" failed.\n("+d+": "+p+")",s.name="ChunkLoadError",s.type=d,s.request=p,o[1](s)}},"chunk-"+n,n)}else e[n]=0},r.O.j=n=>0===e[n];var f=(n,i)=>{var s,u,[o,a,c]=i,l=0;for(s in a)r.o(a,s)&&(r.m[s]=a[s]);if(c)var d=c(r);for(n&&n(i);l<o.length;l++)r.o(e,u=o[l])&&e[u]&&e[u][0](),e[o[l]]=0;return r.O(d)},t=self.webpackChunkalumnos_app=self.webpackChunkalumnos_app||[];t.forEach(f.bind(null,0)),t.push=f.bind(null,t.push.bind(t))})()})();