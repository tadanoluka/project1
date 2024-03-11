import { getCredentials, removeCredentials } from "@/scripts/auth";

export const fetcher = async (url, push) => {
  const res = await fetch(url, {
    headers: {
      Authorization: `Basic ${getCredentials()}`,
    },
  });

  if (res.status === 401) {
    removeCredentials();
    if (push) push("/signin");
  }

  if (res.status === 403) {
    removeCredentials();
    if (push) push("/signin");
  }

  return res;
};
