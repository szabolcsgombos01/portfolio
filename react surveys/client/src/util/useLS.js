//localStorage

import React, { useEffect, useState } from "react";

function useLocalState(defaultValue, key) {
  const [value, setValue] = useState(() => {
    const LSV = localStorage.getItem(key);

    return LSV !== null ? JSON.parse(LSV) : defaultValue;
  });
  useEffect(() => {
    localStorage.setItem(key, JSON.stringify(value));
  }, [key, value]);

  return [value, setValue];
}

export { useLocalState };
